package com.alambagaskara.addimage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterList(var mContext: ListFragment, var listBarang:List<Barang>):
    RecyclerView.Adapter<AdapterList.ViewHolderBarang>() {
    class ViewHolderBarang(itemView:View): RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.image_barang)
        var textNama = itemView.findViewById<TextView>(R.id.text_nama_barang)
        var namaB = itemView.findViewById<TextView>(R.id.nama_barang)
        var textJumlah = itemView.findViewById<TextView>(R.id.text_jumlah_barang)
        var jumlahB = itemView.findViewById<TextView>(R.id.jumlah_barang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBarang {
        var inflater = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolderBarang(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolderBarang, position: Int) {
        var daftarBaru = listBarang[position]

        holder.textNama.text = "Nama Barang"
        holder.namaB.text = daftarBaru.Nama
        holder.textJumlah.text = "Jumlah Barang"
        holder.jumlahB.text = daftarBaru.jumlah
//        holder.img.loadImage(daftarBaru.photo)
        Glide.with(mContext)
            .load(daftarBaru.photo)
            .into(holder.img)


        holder.itemView.setOnClickListener {
            var Gambar = daftarBaru.photo
            var Nama = daftarBaru.Nama
            var Jumlah = daftarBaru.jumlah
            var Beli = daftarBaru.beli
            var Jual = daftarBaru.jual
            var Deskripsi = daftarBaru.deskripsi
            var mIntent = Intent(mContext.requireContext(), DetailList::class.java)

            mIntent.putExtra("GAMBAR", Gambar)
            mIntent.putExtra("NAMA", Nama)
            mIntent.putExtra("JUMLAH", Jumlah)
            mIntent.putExtra("BELI", Beli)
            mIntent.putExtra("JUAL", Jual)
            mIntent.putExtra("DESKRIPSI", Deskripsi)

            mContext.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int = listBarang.size
}