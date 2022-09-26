package space.irsi7.cryptoviewer.coin
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import space.irsi7.cryptoviewer.MainActivity
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Token
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.ui.main.MainViewModel
//import space.irsi7.gallerymy.PictureAdapter.GenerateThumb
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// Класс адаптера для привязки и показа данных в RecyclerView
class CoinAdapter internal constructor(context: Context?, tokens: List<Token>?, values: List<Value>?, viewModel: MainViewModel) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder?>() {

    var viewModel: MainViewModel
    var tokens: List<Token>
    var values: List<Value>
    var isEUR = false

    private var context: Context


    // Функция для обновления списка для показа новых добавленных файлов
    fun updateValuesSet(values: List<Value>?) {
        this.values = values!!
        isEUR = !isEUR
        notifyDataSetChanged()
    }

    // Вложенный класс класса FileAdapter для хранения объектов элементов RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var symbol: TextView
        var change: TextView
        var thumb: ImageView
        var price: TextView

        init {
            title = itemView.findViewById(R.id.title)
            thumb = itemView.findViewById(R.id.thumb)
            symbol = itemView.findViewById(R.id.symbol)
            price = itemView.findViewById(R.id.price)
            change = itemView.findViewById(R.id.change)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.token_item, parent, false)
        return ViewHolder(v)
    }

    // Функция вызывается при присвоении значений переменным класса ViewHolder
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        var prefix = "$ "
        if(isEUR){
            prefix = "€ "
        }
        holder.title.text = tokens[position].name
        holder.symbol.text = tokens[position].symbol.uppercase()
        holder.price.text = prefix + values[position].current_price
        holder.itemView.setOnClickListener {
            viewModel.getCoinInfo(tokens[position].id)
        }

        var change = values[position].price_change_percentage_24h
        if(change.startsWith("-")){
            holder.change.setTextColor(getColor(context, R.color.redDown))
        } else {
            change = "+$change"
            holder.change.setTextColor(getColor(context, R.color.greenUP))
        }
        holder.change.text = change
        Picasso.with(context)
            .load(tokens[position].image)
            .placeholder(R.drawable.placeholder)
            //.error(R.drawable.user_placeholder_error)
            .into(holder.thumb);
    }

    override fun getItemCount(): Int {
        return tokens.size
    }

    init {
        this.viewModel = viewModel
        this.tokens = tokens!!
        this.values = values!!
        this.context = context!!
    }
}