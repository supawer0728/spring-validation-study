package org.parfait.study.springvalidation.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.parfait.study.springvalidation.vaidation.PhoneNumber;
import org.parfait.study.springvalidation.vaidation.group.Create;
import org.parfait.study.springvalidation.vaidation.group.Update;

import lombok.Data;
import lombok.Value;

@Value
public class Member {
    UUID id;
    String nickname;
    LocalDate birthday;
    String phoneNumber;
    LocalDateTime activatedAt;

    public static Member create(Command command) {
        return new Member(UUID.randomUUID(), command.nickname, command.birthday, command.phoneNumber, command.activatedAt);
    }

    public Member update(Command command) {
        return new Member(id, command.nickname, command.birthday, command.phoneNumber, this.activatedAt);
    }

    @Data
    public static class Command {
        @NotNull(groups = Update.class)
        UUID id;
        @NotBlank(message = "{Member.nickname.NotBlank}", groups = { Create.class, Update.class })
        String nickname;
        LocalDate birthday;
        @PhoneNumber(message = "{Member.phoneNumber.PhoneNumber}", groups = { Create.class, Update.class })
        String phoneNumber;
        @NotNull(groups = Create.class)
        @Future(message = "{Member.activatedAt.Future}", groups = { Create.class })
        LocalDateTime activatedAt;
    }
}
