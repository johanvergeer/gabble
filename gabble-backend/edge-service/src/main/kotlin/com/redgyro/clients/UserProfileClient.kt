package com.redgyro.clients

import com.redgyro.dto.userprofiles.UserProfileDto
import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

@Component
@FeignClient(value = "user-profiles-service")
@RibbonClient(value = "user-profiles-service")
interface UserProfileClient {
    @GetMapping(value = ["/user-profiles"])
    fun getAllUserProfiles(): List<UserProfileDto>
}
