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

class RecycleViewAdapter(var lista:List<ItemRSS>, val ctx : Context)
                            : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    //metodo que povoa a recyclerview a partir do objeto view holder
    override fun onBindViewHolder(viewHolder: RecycleViewAdapter.ViewHolder, position: Int) {
        val item = lista[position]
        viewHolder.titulo.text = item.title
        viewHolder.data.text = item.pubDate


        viewHolder.titulo.setOnClickListener {
            //direciona para navegador
            val i = Intent(ACTION_VIEW)
            i.data = Uri.parse(item.link)
            ctx.startActivity(i)
        }
    }

    //class de viewholder para definir os elementos que estarão presentes
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo = view.item_titulo
        val data = view.item_data
    }
}