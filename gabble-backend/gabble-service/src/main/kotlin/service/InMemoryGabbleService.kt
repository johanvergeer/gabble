package service

import com.redgyro.dto.gabbles.GabbleDto
import randomUUID
import java.time.LocalDateTime

class InMemoryGabbleService : GabbleService {

    private val gabbles = mutableSetOf<GabbleDto>()

    init {
        create(GabbleDto(text = "Gabble 1 from user johan_vergeer with tag #tag1", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusDays(1)))
        create(GabbleDto(text = "Gabble 2 from user johan_vergeer with #tag1 and #tag2", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(20)))
        create(GabbleDto(text = "Gabble 3 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(5)))
        create(GabbleDto(text = "Gabble 1 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(15)))
        create(GabbleDto(text = "Gabble 2 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(8)))
        create(GabbleDto(text = "Gabble 1 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(7)))
        create(GabbleDto(text = "Gabble 2 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(18)))
        create(GabbleDto(text = "Gabble 1 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(19)))
        create(GabbleDto(text = "Gabble 2 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(21)))
        create(GabbleDto(text = "Gabble 3 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(35)))
        create(GabbleDto(text = "Gabble 1 from user matthew_murdock", createdById = "00ugl9afjiwNub6yth11", createdByUsername = "matthew_murdock", createdOn = LocalDateTime.now().minusHours(41)))
    }

    override fun findByUserId(userId: String): Set<GabbleDto> {
        return this.gabbles.filter { dto -> dto.createdById == userId }.toSet()
    }

    override fun create(gabbleDto: GabbleDto): GabbleDto {
        val toSave = if (gabbleDto.createdOn == null)
            gabbleDto
                .copy(id = randomUUID(), createdOn = LocalDateTime.now())
                .extractTags()
        else
            gabbleDto
                .copy(id = randomUUID())
                .extractTags()

        this.gabbles.add(toSave)

        return toSave
    }

    override fun findAllGabbleTags(): Set<String> = this.gabbles.flatMap { gabble -> gabble.tags }.toSet()

    private fun GabbleDto.extractTags(): GabbleDto {
        val tags = this.text.split(" ")
            .filter { it -> it.startsWith("#") }
            .toMutableSet()

        return this.copy(tags = tags)
    }
}