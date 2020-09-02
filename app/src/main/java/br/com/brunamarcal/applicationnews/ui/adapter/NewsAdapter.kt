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
import java.text.SimpleDateFormat

class NewsAdapter(private val newsList: List<NewsResult>): RecyclerView.Adapter<NewsAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return AdapterViewHolder(itemView)
    }

    override fun getItemCount() = newsList.count()

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val txtTitle = itemView.txtNewsTitle
        private val txtDate = itemView.txtNewsDate
        private val image = itemView.urlToImage
        private val picasso = Picasso.get()

        fun bind(news: NewsResult){
            txtTitle.text = news.title
            txtDate.text = dataConversion(news.publishedAt, DATE_FORMAT_PATTERN, DATE_PATTERN)


            news.urlToImage.let {
                picasso.load(news.urlToImage).into(image)
            }
        }
    }

    companion object {
        fun dataConversion(date: String, dateFormatPattern: String, datePattern: String): String{
            val inputFormat = SimpleDateFormat(dateFormatPattern)
            val outputFormat = SimpleDateFormat(datePattern)
            val _date = inputFormat.parse(date)

            return outputFormat.format(_date)

        }
        private val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private val DATE_PATTERN = "dd/MM/yyyy"
    }
}