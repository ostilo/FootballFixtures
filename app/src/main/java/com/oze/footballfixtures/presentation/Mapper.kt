package com.oze.footballfixtures.presentation

fun MatchResponse.map() = DomainEntities.DomainMatchResponse(
    matches = matches.map { it.map() }
)

fun DomainEntities.DomainMatchResponse.map() = MatchResponse(
    matches = matches.map { it.map() }
)

fun Match.map() = DomainEntities.DomainMatch(
    id = id,
    competition = competition?.map(),
    season = season.map(),
    utcDate = utcDate,
    status = status,
    matchday = matchday,
    stage = stage,
    group = group,
    lastUpdated = lastUpdated,
    score = score.map(),
    homeTeam = homeTeam.map(),
    awayTeam = awayTeam.map()
)

fun DomainEntities.DomainMatch.map() = Match(
    id = id,
    competition = competition?.map(),
    season = season.map(),
    utcDate = utcDate,
    status = status,
    matchday = matchday,
    stage = stage,
    group = group,
    lastUpdated = lastUpdated,
    score = score.map(),
    homeTeam = homeTeam.map(),
    awayTeam = awayTeam.map()
)

fun Season.map() = DomainEntities.DomainSeason(id = id, startDate = startDate, endDate = endDate)

fun DomainEntities.DomainSeason.map() = Season(id = id, startDate = startDate, endDate = endDate)

fun Score.map() =
    DomainEntities.DomainScore(
        halfTime = halfTime.map(),
        fullTime = fullTime.map(),
        duration = duration
    )

fun DomainEntities.DomainScore.map() =
    Score(halfTime = halfTime.map(), fullTime = fullTime.map(), duration = duration)

fun SubScores.map() = DomainEntities.DomainSubScores(homeTeam = homeTeam, awayTeam = awayTeam)

fun DomainEntities.DomainSubScores.map() = SubScores(homeTeam = homeTeam, awayTeam = awayTeam)

fun SubTeams.map() = DomainEntities.DomainSubTeams(id = id, name = name)

fun DomainEntities.DomainSubTeams.map() = SubTeams(id = id, name = name)

fun CompetitionResponse.map() = DomainEntities.DomainCompetitionResponse(
    competitions = competitions.map { it.map() }
)

fun DomainEntities.DomainCompetitionResponse.map() = CompetitionResponse(
    competitions = competitions.map { it.map() }
)

fun Competitions.map() =
    DomainEntities.DomainCompetitions(id = id, name = name, currentSeason = currentSeason.map())

fun DomainEntities.DomainCompetitions.map() =
    Competitions(id = id, name = name, currentSeason = currentSeason.map())

fun TeamResponse.map() = DomainEntities.DomainTeamResponse(
    teams = teams.map { it.map() }
)

fun DomainEntities.DomainTeamResponse.map() = TeamResponse(
    teams = teams.map { it.map() }
)

fun Team.map() =
    DomainEntities.DomainTeam(
        id = id,
        name = name,
        shortName = shortName,
        crestUrl = crestUrl ?: ""
    )

fun DomainEntities.DomainTeam?.map() =
    Team(id = this?.id ?: 1, name = this?.name ?: "", shortName = this?.shortName ?: "", crestUrl = this?.crestUrl)

fun PlayerResponse.map() = DomainEntities.DomainPlayerResponse(
    id = id,
    name = name,
    shortName = shortName,
    crestUrl = crestUrl ?: "",
    squad = squad.map { it.map() }
)

fun DomainEntities.DomainPlayerResponse?.map() = PlayerResponse(
    id = this!!.id,
    name = name,
    shortName = shortName,
    crestUrl = crestUrl,
    squad = squad.map { it.map() }
)

fun Player.map() =
    DomainEntities.DomainPlayer(
        id = id,
        name = name,
        position = position ?: "",
        role = role,
        count = count
    )

fun DomainEntities.DomainPlayer?.map() =
    Player(id = this!!.id, name = name, position = position, role = role, count = count)

fun StandingResponse.map() = DomainEntities.DomainStandingResponse(
    standings = standings.map { it.map() }
)

fun DomainEntities.DomainStandingResponse.map() = StandingResponse(
    standings = standings.map { it.map() }
)

fun Standing?.map() = DomainEntities.DomainStanding(table = this!!.table.map { it.map() })

fun DomainEntities.DomainStanding.map() = Standing(table = table.map { it.map() })

fun Table.map() = DomainEntities.DomainTable(
    position = position,
    team = team.map(),
    playedGames = playedGames,
    points = points,
    goalDifference = goalDifference
)

fun DomainEntities.DomainTable.map() = Table(
    position = position,
    team = team.map(),
    playedGames = playedGames,
    points = points,
    goalDifference = goalDifference
)