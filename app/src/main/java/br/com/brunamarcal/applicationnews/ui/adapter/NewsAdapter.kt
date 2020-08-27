package br.com.brunamarcal.applicationnews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.applicationnews.BuildConfig
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.model.NewsResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(val newsList: List<NewsResult>): RecyclerView.Adapter<NewsAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.AdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return AdapterViewHolder(itemView)
    }

    override fun getItemCount() = newsList.count()

    override fun onBindViewHolder(holder: NewsAdapter.AdapterViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val txtTitle = itemView.txtNewsTitle
        private val txtDate = itemView.txtNewsDate
        private val image = itemView.urlToImage
        private val picasso = Picasso.get()

        fun bind(news: NewsResult){
            txtTitle.text = news.title
            txtDate.text = news.publishedAt

            news.urlToImage.let {
                picasso.load("""${BuildConfig.BASE_URL_IMAGE}${news.urlToImage}""")
                    .into(image)
            }
        }
    }
}