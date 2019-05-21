package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*

class RecycleViewAdapter(var listItens:List<ItemRSS>, val ctx : Context)
                            : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listItens.size

    //metodo que povoa a recyclerview a partir do objeto view holder
    override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, position: Int) {
        val item = listItens[position]
        holder.titulo.text = item.title
        holder.data.text = item.pubDate

        holder.titulo.setOnClickListener {
            val i = Intent(ACTION_VIEW)
            i.data = Uri.parse(item.link)
            ctx.startActivity(i)
        }
    }

    //class de viewholder para definir os elementos que estarão presentes
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.item_titulo
        val data = itemView.item_data
    }
}