package com.faishalbadri.notepad.di;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.repository.notes.NotesDataLocal;
import com.faishalbadri.notepad.repository.notes.NotesRepository;

public class NotesRepositoryInject {
    public static NotesRepository provideTo(RoomClient database) {
        return new NotesRepository(new NotesDataLocal(database));
    }
}
