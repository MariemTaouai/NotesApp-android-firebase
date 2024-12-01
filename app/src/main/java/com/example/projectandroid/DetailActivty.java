package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivty extends AppCompatActivity {

    TextView Title;
    TextView Note;
    ImageView delete;
    ImageView Update;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);

        // Initialisation des éléments de l'interface
        Title = findViewById(R.id.Title);
        Note = findViewById(R.id.Note);
        delete = findViewById(R.id.delete);
        Update = findViewById(R.id.update);

        // Récupérer les données passées dans l'Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Title.setText(bundle.getString("Title"));
            Note.setText(bundle.getString("Note"));
            key = bundle.getString("key");
        }

        // Fonction pour supprimer la note
        delete.setOnClickListener(view -> {
            if (key != null && !key.isEmpty()) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes");
                Log.d("FirebaseDelete", "Suppression de la clé: " + key);

                reference.child(key).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            // Suppression réussie
                            Toast.makeText(DetailActivty.this, "Note supprimée avec succès", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AcceuilActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            // En cas d'erreur
                            Toast.makeText(DetailActivty.this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(DetailActivty.this, "Clé invalide, suppression impossible", Toast.LENGTH_SHORT).show();
            }
        });

        // Fonction pour mettre à jour la note
        Update.setOnClickListener(view -> {
            if (key != null && !key.isEmpty()) {
                Intent intent = new Intent(DetailActivty.this, modifyActivity.class);
                intent.putExtra("NOTE_KEY", key); // Passer la clé de la note
                intent.putExtra("NOTE_TITLE", Title.getText().toString()); // Passer le titre
                intent.putExtra("NOTE_TEXT", Note.getText().toString()); // Passer le texte de la note
                startActivity(intent);
            } else {
                Toast.makeText(DetailActivty.this, "Clé invalide, mise à jour impossible", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
