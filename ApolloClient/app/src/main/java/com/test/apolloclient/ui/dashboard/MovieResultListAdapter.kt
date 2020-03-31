package com.vishion.app.ui.productresult

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.R
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.movie_list_item.view.*

interface OnListFragmentInteractionListener {
    fun onListFragmentInteraction(item: MoviesQuery.Movie?)
}

class MovieResultListAdapter(
    private val mValues: List<MoviesQuery.Movie?>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MovieResultListAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MoviesQuery.Movie?
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.

            notifyDataSetChanged()
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        item?.let {
            val radius = 8
            val margin = 5
            val transformation: Transformation = RoundedCornersTransformation(radius, margin)
             Picasso.get().load("https://image.tmdb.org/t/p/w220_and_h330_face/${item.poster_path}").transform(transformation).into(holder.imgMovie)

            holder.txtMovieTitle.text = "Title: ${item.original_title}"
            holder.txtVoteAvg.text = "Rating: ${item.vote_average}"
        }


        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imgMovie: ImageView = mView.imgMovie
        val txtMovieTitle: TextView = mView.txtMovieTitle
        val txtVoteAvg: TextView = mView.txtVoteAvg
    }
}