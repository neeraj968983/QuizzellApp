package com.example.quizellapp
import android.graphics.Color
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
            holder.rank.setBackgroundColor(Color.parseColor("#491967"))
            holder.username.setBackgroundColor(Color.parseColor("#491967"))
            holder.score.setBackgroundColor(Color.parseColor("#491967"))
        }
        if(position == 0){
            holder.rank.setTextColor(Color.parseColor("#FFD700"))
            holder.username.setTextColor(Color.parseColor("#FFD700"))
            holder.score.setTextColor(Color.parseColor("#FFD700"))
        }
        if(position == 1){
            holder.rank.setTextColor(Color.parseColor("#C0C0C0"))
            holder.username.setTextColor(Color.parseColor("#C0C0C0"))
            holder.score.setTextColor(Color.parseColor("#C0C0C0"))
        }
        if(position == 2){
            holder.rank.setTextColor(Color.parseColor("#CD7F32"))
            holder.username.setTextColor(Color.parseColor("#CD7F32"))
            holder.score.setTextColor(Color.parseColor("#CD7F32"))
        }
        holder.rank.text = Rank.toString()
        holder.username.text = UserName
        holder.score.text = Score.toString()
    }
    override fun getItemCount(): Int {
        return CAUserName.size
    }
}
