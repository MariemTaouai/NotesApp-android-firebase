package com.example.projectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> dataList;

    public NoteAdapter(Context context, List<Note> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for each item
        View view = LayoutInflater.from(context).inflate(R.layout.note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Get the note for the current position
        Note note = dataList.get(position);

        // Set title and note text
        holder.title.setText(note.getTitre());
        holder.note.setText(note.getNoteText());

        // OnClickListener for the cardView to open DetailActivity
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivty.class);
            // Pass the data to the DetailActivity
            intent.putExtra("Title", note.getNoteText());
            intent.putExtra("Note", note.getTitre());
            intent.putExtra("key", note.getKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the data list
        return dataList.size();
    }

    // Method to update the list when searching
    public void searchNoteList(ArrayList<Note> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }

    // ViewHolder class to hold the views for each item
    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView note;
        CardView cardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            title = itemView.findViewById(R.id.Title);
            note = itemView.findViewById(R.id.Note);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
