package br.com.brunamarcal.applicationnews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.model.SourcesResult
import kotlinx.android.synthetic.main.item_sources.view.*

class SourcesAdapter (private val newsSourcesList: List<SourcesResult>, private val clickNews: ((news: SourcesResult) -> Unit)):
    RecyclerView.Adapter<SourcesAdapter.AdapterViewHolderSources>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesAdapter.AdapterViewHolderSources {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sources, parent, false)
        return AdapterViewHolderSources (itemView, clickNews)
    }

    override fun getItemCount() = newsSourcesList.count()

    override fun onBindViewHolder(holder: SourcesAdapter.AdapterViewHolderSources, position: Int) {
        holder.bind(newsSourcesList[position])
    }

        class AdapterViewHolderSources(itemView: View, private val clickNews: (news: SourcesResult) -> Unit): RecyclerView.ViewHolder(itemView){
        private val txtSourcesName = itemView.txtSoucersName
        private val txtSourcesDescription = itemView.txtSourcesDescription
        private val txtUrl = itemView.txtUrlSources

            fun bind(news: SourcesResult){
                txtSourcesName.text = news.name
                txtSourcesDescription.text = news.description
                txtUrl.text = news.url

            itemView.setOnClickListener {
                clickNews.invoke(news)
            }
        }
    }
}