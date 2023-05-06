package lynx.internship.tv.ui.commonRecycleViewElements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lynx.internship.tv.databinding.CommonRvItemLayoutBinding
import lynx.internship.tv.data.api.Movie

class CommonRVAdapter(val items: List<Movie>, val commonRVAdapterClickListener: CommonRVAdapterClickListener) : RecyclerView.Adapter<CommonRVAdapter.CommonRVViewHolder>() {

    interface CommonRVAdapterClickListener{
        fun onItemClick(commonRVItem: Movie)
    }

    class CommonRVViewHolder(val binding: CommonRvItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(commonRVItem: Movie, itemClickListener: CommonRVAdapterClickListener){
            binding.tvTitle.text = commonRVItem.title
            Glide.with(itemView.context).load(commonRVItem.imageUrl).into(binding.ivImage)
            binding.commonRVItem.setOnClickListener{
                itemClickListener.onItemClick(commonRVItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonRVViewHolder {

        return CommonRVViewHolder(
            CommonRvItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: CommonRVViewHolder, position: Int) {
        val commonRVItem = items[position];
        holder.bind(commonRVItem, object : CommonRVAdapterClickListener{
            override fun onItemClick(commonRVItem: Movie) {
                commonRVAdapterClickListener.onItemClick(commonRVItem)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size;
    }
}