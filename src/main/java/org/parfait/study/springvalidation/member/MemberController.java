package org.parfait.study.springvalidation.member;

import java.util.UUID;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.parfait.study.springvalidation.member.entity.Member;
import org.parfait.study.springvalidation.vaidation.group.Create;
import org.parfait.study.springvalidation.vaidation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    @PostMapping
    public Member post(@Validated(Create.class) @RequestBody Member.Command createRequest) {
        return Member.create(createRequest);
    }

    @PutMapping("/{id}")
    public Member patch(@PathVariable UUID id,
                        @Validated(Update.class) @RequestBody Member.Command updateRequest) {
        Member origin = getOrigin(updateRequest.getId());
        return origin.update(updateRequest);
    }

    private Member getOrigin(UUID id) {
        EasyRandomParameters parameters = new EasyRandomParameters().randomize(UUID.class, () -> id);
        EasyRandom random = new EasyRandom(parameters);
        return random.nextObject(Member.class);
    }
}
