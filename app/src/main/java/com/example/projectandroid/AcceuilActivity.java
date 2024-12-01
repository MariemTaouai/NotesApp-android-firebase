package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcceuilActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // RecyclerView pour afficher les notes
    private NoteAdapter noteAdapter;
    private List<Note> noteList; // Liste de notes
    private FirebaseDatabase database; // Référence Firebase
    private Button add; // Bouton pour ajouter une note
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil); // Layout principal pour l'accueil

        // **1. Initialiser les vues**
        add = findViewById(R.id.add); // Bouton pour ajouter une note
        recyclerView = findViewById(R.id.notes); // RecyclerView pour afficher les notes
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        // **2. Configuration du RecyclerView**
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Affichage en une colonne

        // Initialiser la liste et l'adaptateur
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList); // Passer le contexte et la liste à l'adaptateur
        recyclerView.setAdapter(noteAdapter); // Associer l'adaptateur au RecyclerView

        // **3. Initialiser Firebase**
        database = FirebaseDatabase.getInstance();

        // Charger les données depuis Firebase
        loadNotes();

        // **4. Ajouter une nouvelle note**
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AcceuilActivity.this, AddActivity.class));
            }
        });
    }

    /**
     * Méthode pour charger les notes depuis Firebase et les afficher dans le RecyclerView
     */
    private void loadNotes() {
        DatabaseReference notesRef = database.getReference("Notes"); // Référence à "Notes" dans Firebase

        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList.clear(); // Efface les anciennes données pour éviter les doublons
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    note.setKey(noteSnapshot.getKey());
                    // Convertir les données en objet Note
                    if (note != null) {
                        noteList.add(note); // Ajouter chaque note à la liste
                    }
                }
                noteAdapter.notifyDataSetChanged(); // Notifier l'adaptateur des changements
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Afficher un message d'erreur
                Toast.makeText(AcceuilActivity.this, "Erreur : " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    // Méthode pour filtrer la liste des notes en fonction du titre
    public void searchList(String Titre) {
        ArrayList<Note> searchList = new ArrayList<>();
        for (Note note : noteList) {
            if (note.getTitre().toLowerCase().contains(Titre.toLowerCase())) {
                searchList.add(note); // Ajouter les notes qui correspondent à la recherche
            }
        }
        noteAdapter.searchNoteList(searchList); // Mettre à jour la liste de l'adaptateur
    }
}
