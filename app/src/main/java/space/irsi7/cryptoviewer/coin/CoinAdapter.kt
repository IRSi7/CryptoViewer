package space.irsi7.cryptoviewer.coin
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import space.irsi7.cryptoviewer.MainActivity
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Token
import space.irsi7.cryptoviewer.model.Value
//import space.irsi7.gallerymy.PictureAdapter.GenerateThumb
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// Класс адаптера для привязки и показа данных в RecyclerView
class CoinAdapter internal constructor(context: Context?, tokens: List<Token>?, values: List<Value>?) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder?>() {

    var tokens: List<Token>
    var values: List<Value>

    private val thumbs: HashMap<String, Bitmap> = HashMap()

    private var context: Context


    // Функция для обновления списка для показа новых добавленных файлов
    fun updateValuesSet(values: List<Value>?) {
        this.values = values!!
        notifyDataSetChanged()
    }

    // Вложенный класс класса FileAdapter для хранения объектов элементов RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var symbol: TextView
        var change: TextView
        var price: TextView

        init {
            title = itemView.findViewById(R.id.title)
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
        holder.title.text = tokens[position].name
        holder.symbol.text = tokens[position].symbol
        holder.price.text = values[position].current_price.toString()
        holder.change.text = values[position].price_change_percentage_24h.toString()

//        if (thumbs.containsKey(tokens[position].absolutePath)) {
//            holder.thumb.setImageBitmap(thumbs[tokens[position].absolutePath])
//        } else {
//            holder.thumb.setImageDrawable(
//                context.resources.getDrawable(R.drawable.placeholder)
//            )
//
//            var thumb: Bitmap
//            val job: Job = GlobalScope.launch { withContext(Dispatchers.IO) {
//                thumb =
//                    ThumbnailUtils.extractThumbnail(
//                        BitmapFactory.decodeFile(tokens[position].absolutePath),
//                        256,
//                        256
//                    )
//            }
//                withContext(Dispatchers.Main){
//                    thumbs[tokens[position].absolutePath] = thumb
//                    holder.thumb.setImageBitmap(thumb)
//
//                    }
//                    }
//                }

        holder.itemView.setOnClickListener {
//            val fullScreen = Intent(context, MainActivity::class.java)
//            fullScreen.putExtra("path", tokens[position].absolutePath)
//            fullScreen.putExtra("position", position)
//
//            startActivity(context, fullScreen, null)
        }

    }

    override fun getItemCount(): Int {
        return tokens.size
    }

    init {
        this.tokens = tokens!!
        this.values = values!!
        this.context = context!!
    }
}