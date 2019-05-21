package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ListView
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MainActivity : Activity() {

    //ao fazer envio da resolucao, use este link no seu codigo!
    private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"

    //OUTROS LINKS PARA TESTAR...
    //http://rss.cnn.com/rss/edition.rss
    //http://pox.globo.com/rss/g1/brasil/
    //http://pox.globo.com/rss/g1/ciencia-e-saude/
    //http://pox.globo.com/rss/g1/tecnologia/

    //inicicializando RV
    private var recycleView: RecyclerView? = null
    private var adapter: RecycleViewAdapter = RecycleViewAdapter(emptyList(), this@MainActivity)
    
    //private var conteudoRSS: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById(R.id.conteudoRSS)

        recycleView?.layoutManager = LinearLayoutManager(applicationContext)
        recycleView?.adapter = adapter

    }


    override fun onStart() {
        super.onStart()
        //thread pra rodar.
        doAsync {


        try {

            val feedXML = getRssFeed(RSS_FEED)
            val listaRSS =  ParserRSS.parse(feedXML)
           //necessario pra fazer rodar o notify(atualizar a view)
            uiThread {
                //carrega os itens.
                adapter.lista = listaRSS
                    //avisa  ao adapter que foi alterado os seus elementos
                adapter.notifyDataSetChanged()
                }
           // conteudoRSS?.text = lista[1]

        } catch (e: IOException) {
            e.printStackTrace()
        }
        }
    }

    //Opcional - pesquise outros meios de obter arquivos da internet - bibliotecas, etc.
    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
        var input: InputStream? = null
        var rssFeed = ""
        try {
            val url = URL(feed)
            val conn = url.openConnection() as HttpURLConnection
            input = conn.getInputStream()
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count: Int = input.read(buffer)
            while (count  != -1) {
                out.write(buffer, 0, count)
                count = input.read(buffer)
            }
            val response = out.toByteArray()
            rssFeed = String(response, Charset.forName("UTF-8"))
        } finally {
            if ( input != null) {
                input!!.close()
            }
        }
        return rssFeed
    }
}
