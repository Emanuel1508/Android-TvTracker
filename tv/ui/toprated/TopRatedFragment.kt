package lynx.internship.tv.ui.toprated

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import lynx.internship.tv.data.api.Movie
import lynx.internship.tv.databinding.FragmentTopRatedBinding
import lynx.internship.tv.ui.commonRecycleViewElements.CommonRVAdapter
import lynx.internship.tv.ui.details.DetailsActivity

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopRatedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        viewModel.fetchTopRated()
    }

    private fun observeData() {
        viewModel.topRatedListLiveData.observe(viewLifecycleOwner) { movieList ->
            binding.rvTopRated.layoutManager =
                LinearLayoutManager(this@TopRatedFragment.context)

            binding.rvTopRated.adapter = CommonRVAdapter(
                movieList,
                object : CommonRVAdapter.CommonRVAdapterClickListener {
                    override fun onItemClick(movie: Movie) {
                        val intent = Intent(
                            this@TopRatedFragment.context,
                            DetailsActivity::class.java
                        )
                        intent.putExtra("id", movie.id)
                        startActivity(intent)
                    }
                })
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(this@TopRatedFragment.context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}