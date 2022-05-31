package com.example.quizellapp
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


internal class CustomAdapter(private var CArank: List<Int>, private var CAUserName: List<String>, private var CAScore: List<Double>, private  var userName:String? ) :
        RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rank: TextView = view.findViewById(R.id.Rank)
        var username: TextView = view.findViewById(R.id.Username)
        var score: TextView = view.findViewById(R.id.Score)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_cardview, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Rank = CArank[position]
        val UserName = CAUserName[position]
        val Score = CAScore[position]
        if (UserName.equals(userName)){
            holder.rank.setBackgroundColor(Color.parseColor("#6E05AF"))
            holder.rank.setTypeface(Typeface.DEFAULT_BOLD)
            holder.rank.setTextSize(20F)
            holder.username.setBackgroundColor(Color.parseColor("#6E05AF"))
            holder.username.setTypeface(Typeface.DEFAULT_BOLD)
            holder.username.setTextSize(20F)
            holder.score.setBackgroundColor(Color.parseColor("#6E05AF"))
            holder.score.setTypeface(Typeface.DEFAULT_BOLD)
            holder.score.setTextSize(20F)
        }
        if(position == 0){
            holder.rank.setBackgroundResource(R.drawable.medalforfirst)
            holder.rank.setText("")
            holder.username.setTextColor(Color.parseColor("#FFD700"))
            holder.score.setTextColor(Color.parseColor("#FFD700"))
        }
        if(position == 1){
            holder.rank.setBackgroundResource(R.drawable.medalforsecond)
            holder.rank.setText("")
            holder.username.setTextColor(Color.parseColor("#C0C0C0"))
            holder.score.setTextColor(Color.parseColor("#C0C0C0"))
        }
        if(position == 2){
            holder.rank.setBackgroundResource(R.drawable.medalforthird)
            holder.rank.setText("")
            holder.username.setTextColor(Color.parseColor("#CD7F32"))
            holder.score.setTextColor(Color.parseColor("#CD7F32"))
        }
        if (position>2){
            holder.rank.text = Rank.toString()
        }
        holder.username.text = UserName
        holder.score.text = Score.toString()
    }
    override fun getItemCount(): Int {
        return CAUserName.size
    }
}
