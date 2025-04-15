package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.repository.SessionRepo;
import com.main.prevoyancehrm.entities.Sessions;
import com.main.prevoyancehrm.service.serviceInterface.SessionService;

@Service
public class SessionServiceImpl implements SessionService{

    @Autowired
    private SessionRepo SessionRepo;

    @Override
    public Sessions addSession(Sessions session) {
        return this.SessionRepo.save(session);
    }

    @Override
    public Sessions getSessionById(long id) {
        return this.SessionRepo.findBySessionId(id).orElse(null);
    }

    @Override
    public Sessions getSessionByToken(String token) {
        return this.SessionRepo.findByToken(token).orElse(null);
    }
    
}
