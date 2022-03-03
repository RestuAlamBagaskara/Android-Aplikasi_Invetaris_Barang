package com.alambagaskara.addimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_halaman_list_barang.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.rv_barang

class ListFragment : Fragment() {

    private var mStorage : FirebaseStorage? = null
    private var mDatabaseRef : DatabaseReference? = null
    private var mDBListener : ValueEventListener? = null
    private lateinit var mBarang : MutableList<Barang>
    private lateinit var listAdapter: AdapterList
    private lateinit var auth: FirebaseAuth
//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<AdapterBarang.ViewHolderBarang>? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_barang.apply {
            rv_barang.setHasFixedSize(true)
            rv_barang.layoutManager = LinearLayoutManager(this@ListFragment.requireContext())

            mBarang = ArrayList()
            listAdapter = AdapterList(this@ListFragment, mBarang)
            rv_barang.adapter = listAdapter

            //set Firebase Database
            auth = FirebaseAuth.getInstance()
            val UID = Firebase.auth.currentUser?.uid.toString()
            mStorage = FirebaseStorage.getInstance()
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(UID)
            mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    mBarang.clear()
                    for (barangSnapshot in snapshot.children){
                        val upload = barangSnapshot.getValue(Barang::class.java)
                        upload!!.Nama = barangSnapshot.key
                        mBarang.add(upload)
                    }
                    listAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}