package com.redgyro.gabblesservice.service

import com.google.inject.Inject
import com.redgyro.dto.gabbles.GabbleDto
import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.gabblesservice.events.UserProfileUpdateEvent
import com.redgyro.gabblesservice.randomUUID
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class InMemoryGabbleService @Inject constructor(private val userProfileService: UserProfileService) : GabbleService {

    private val gabbles = mutableSetOf<GabbleDto>()
    private val userProfilesToUserId = mutableMapOf<String, UserProfileDto>()
    private val userProfilesToUsername = mutableMapOf<String, UserProfileDto>()

    init {
        runBlocking {
            create(GabbleDto(text = "Gabble 1 from user johan_vergeer with tag #tag1", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusDays(1)))
            create(GabbleDto(text = "Gabble 2 from user johan_vergeer with #tag1 and #tag2", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(20)))
            create(GabbleDto(text = "Gabble 3 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(5)))
            create(GabbleDto(text = "Gabble 1 from user floris_bosch at @johan_vergeer", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(15)))
            create(GabbleDto(text = "Gabble 2 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(8)))
            create(GabbleDto(text = "Gabble 1 from user matt_damon at @johan_vergeer", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(7)))
            create(GabbleDto(text = "Gabble 2 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(18)))
            create(GabbleDto(text = "Gabble 1 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(19)))
            create(GabbleDto(text = "Gabble 2 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(21)))
            create(GabbleDto(text = "Gabble 3 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(35)))
            create(GabbleDto(text = "Gabble 1 from user matthew_murdock", createdById = "00ugl9afjiwNub6yth11", createdByUsername = "matthew_murdock", createdOn = LocalDateTime.now().minusHours(41)))
        }

        UserProfileUpdateEvent on { changeMentionedUsername(it.userProfileDto.userId, it.userProfileDto.username) }
    }

    override fun findByUserId(userId: String): Set<GabbleDto> {
        return this.gabbles.filter { dto -> dto.createdById == userId }.toSet()
    }

    override suspend fun create(gabbleDto: GabbleDto): GabbleDto {
        val toSave = if (gabbleDto.createdOn == null)
            gabbleDto
                .copy(id = randomUUID(), createdOn = LocalDateTime.now())
                .extractTags()
                .extractMentions()
        else
            gabbleDto
                .copy(id = randomUUID())
                .extractTags()
                .extractMentions()

        this.gabbles.add(toSave)

        return toSave
    }

    override fun findAllGabbleTags(): Set<String> =
        this.gabbles.flatMap { gabble -> gabble.tags }.toSet()

    override fun findMentionedInForUser(userId: String) =
        this.gabbles.filter { gabble -> gabble.mentions.contains(userId) }.toSet()

    private fun GabbleDto.extractTags(): GabbleDto {
        val tags = this.text.split(" ")
            .filter { it -> it.startsWith("#") }
            .toMutableSet()

        return this.copy(tags = tags)
    }

    private suspend fun GabbleDto.extractMentions(): GabbleDto {
        val mentions = this.text.split(" ")
            .filter { it -> it.startsWith("@") }
            .map { username -> username.drop(1) }
            .map { username ->
                val userProfileFromMap = userProfilesToUsername[username]

                if (userProfileFromMap == null) {
                    val userProfile = userProfileService.findByUsername(username)
                    userProfilesToUserId[userProfile.userId] = userProfile
                    userProfilesToUsername[userProfile.username] = userProfile
                    userProfile.userId
                } else
                    userProfileFromMap.userId
            }
            .toMutableSet()

        return this.copy(mentions = mentions)
    }

    override fun changeMentionedUsername(userId: String, newUserName: String) {
        println("New username $newUserName for user with id $userId")
    }
}