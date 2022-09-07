package com.faishalbadri.notepad.di;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.repository.alquran.local.AlquranLocalDataRemote;
import com.faishalbadri.notepad.repository.alquran.local.AlquranLocalRepository;


public class AlquranLocalRepositoryInject {
    public static AlquranLocalRepository provideTo(RoomClient database) {
        return new AlquranLocalRepository(new AlquranLocalDataRemote(database));
    }
}
