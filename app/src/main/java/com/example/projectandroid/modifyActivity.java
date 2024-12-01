package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class modifyActivity extends AppCompatActivity {

    EditText TitleEdit, NoteEdit;
    Button updateButton;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // Initialiser les éléments de l'interface
        TitleEdit = findViewById(R.id.Titre);
        NoteEdit = findViewById(R.id.Note);
        updateButton = findViewById(R.id.updateButton);

        // Récupérer les données de l'Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("NOTE_KEY");
            TitleEdit.setText(bundle.getString("NOTE_TITLE"));
            NoteEdit.setText(bundle.getString("NOTE_TEXT"));

        }

        // Mise à jour de la note
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = TitleEdit.getText().toString().trim();
                String updatedNote = NoteEdit.getText().toString().trim();

                // Vérification des champs vides
                if (TextUtils.isEmpty(updatedTitle) || TextUtils.isEmpty(updatedNote)) {
                    Toast.makeText(modifyActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mettre à jour la note dans Firebase
                updateNoteInFirebase(updatedTitle, updatedNote);
            }
        });
    }

    private void updateNoteInFirebase(String updatedTitle, String updatedNote) {
        // Référence à Firebase pour la mise à jour
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes");

        // Créer un objet Note avec les nouvelles données
        Note updatedNoteObject = new Note(updatedTitle, updatedNote);

        // Mettre à jour la note dans Firebase en utilisant la clé unique
        reference.child(key).setValue(updatedNoteObject)
                .addOnSuccessListener(aVoid -> {
                    // Mise à jour réussie
                    Toast.makeText(modifyActivity.this, "Note mise à jour avec succès", Toast.LENGTH_SHORT).show();

                    // Retourner à l'écran de détail pour voir les changements
                    Intent intent = new Intent(modifyActivity.this, DetailActivty.class);
                    intent.putExtra("key", key);
                    intent.putExtra("Title", updatedTitle); // Passer le titre mis à jour
                    intent.putExtra("Note", updatedNote); // Passer le texte mis à jour

                    startActivity(intent);
                    finish(); // Fermer ModifyActivity
                });

    }
}
