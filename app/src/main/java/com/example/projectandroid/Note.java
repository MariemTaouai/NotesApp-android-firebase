package com.example.projectandroid;



    public class Note {
        private String titre; // Correspond à "titre" dans Firebase
        private String noteText;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        private String key ;// Correspond à "noteText" dans Firebase

        // Constructeur vide requis par Firebase
        public Note() {
        }

        public Note(String titre, String noteText) {
            this.titre = titre;
            this.noteText = noteText;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getNoteText() {
            return noteText;
        }

        public void setNoteText(String noteText) {
            this.noteText = noteText;
        }
    }

