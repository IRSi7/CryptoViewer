package space.irsi7.cryptoviewer.ui.main.TokenListFragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Token
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.ui.main.MainViewModel
//import space.irsi7.gallerymy.PictureAdapter.GenerateThumb

// Класс адаптера для привязки и показа данных в RecyclerView
class CoinAdapter internal constructor(context: Context?, tokens: List<Token>?, values: List<Value>?, viewModel: MainViewModel) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder?>() {

    var viewModel: MainViewModel
    var tokens: List<Token>
    var values: List<Value>
    var isEUR = true

    private var context: Context


    // Функция для обновления списка для показа новых добавленных файлов
    fun updateValuesSet(values: List<Value>?) {
        this.values = values!!
        isEUR = !isEUR
        notifyDataSetChanged()
    }

    fun updateAdapter(){
        this.tokens = viewModel.tokenData.value!!
        when (viewModel.chosenVal.value) {
            0 -> this.values = viewModel.valueUSD.value!!
            1 -> this.values = viewModel.valueEUR.value!!
        }
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
            .into(holder.thumb)
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