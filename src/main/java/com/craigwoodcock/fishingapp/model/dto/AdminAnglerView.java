package com.craigwoodcock.fishingapp.model.dto;

import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.utils.EmailMasker;

public class AdminAnglerView {
    private final Long id;
    private final String name;
    private final String maskedEmail;
    private final int sessionCount;
    private final int catchCount;
    private final String linkedUsername;

    public AdminAnglerView(Angler angler, int sessionCount, int catchCount, String linkedUsername) {
        this.id = angler.getId();
        this.name = angler.getName();
        this.maskedEmail = EmailMasker.mask(angler.getEmail());
        this.sessionCount = sessionCount;
        this.catchCount = catchCount;

        this.linkedUsername = linkedUsername;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaskedEmail() {
        return maskedEmail;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public int getCatchCount() {
        return catchCount;
    }

    public String getLinkedUsername() {
        return linkedUsername;
    }

    public boolean isLinkedToUser() {
        return linkedUsername != null;
    }
}
