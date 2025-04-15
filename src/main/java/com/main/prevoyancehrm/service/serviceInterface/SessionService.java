package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.entities.Sessions;

public interface SessionService {
    Sessions addSession(Sessions session);
    Sessions getSessionById(long id);
    Sessions getSessionByToken(String token);
}
