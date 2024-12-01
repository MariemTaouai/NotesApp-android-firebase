package com.example.projectandroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private Button addButton;
    private EditText Title;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Initialisation des éléments
        addButton = findViewById(R.id.add);
        Title = findViewById(R.id.Title);
        note = findViewById(R.id.TextAdd);

        // Listener pour le bouton Ajouter
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre = Title.getText().toString().trim();
                String noteText = note.getText().toString().trim();

                // Vérification des champs vides
                if (TextUtils.isEmpty(titre) || TextUtils.isEmpty(noteText)) {
                    Toast.makeText(AddActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Ajouter la note à Firebase
                addNoteToFirebase(titre, noteText);
            }
        });
    }

    private void addNoteToFirebase(String titre, String noteText) {
        // Référence de la table fixe dans Firebase

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notes");

        // Générer une clé unique pour la note
        String key = databaseReference.push().getKey();

        // Préparer les données de la note
        Note note = new Note(titre, noteText);

        // Ajouter les données à Firebase sous la clé générée
        databaseReference.child(key).setValue(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
