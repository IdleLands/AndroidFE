package idle.land.app.logic.api;

public enum StatusCode {
    Badtoken(-1),
    Passwordtooshort(1),
    Nametooshort(2),
    Nametoolong(3),
    Nouniqueidentifier(4),
    Databaseerrorprobablyidentifiertaken(5),
    Nonamespecified(6),
    Notloggedin(10),
    Cantauthviapassword(11),
    Nopasswordset(12),
    Playerdoesntexist(13),
    Badpassword(14),
    Alreadyloggedin(15),
    Nopasswordspecified(16),
    Passwordsetsuccessfully(17),
    Successfullogin(18),
    Successfullogout(19),
    Successfulregister(20),
    Personalityalreadyset(30),
    Cannotusepersonality(31),
    Personalitynotyetset(32),
    Successfulpersonalityupdate(33),
    Invalidslot(40),
    Inventoryfull(41),
    Cantaddemptytoinventory(42),
    Inventoryslotempty(43),
    Cantsellitem(44),
    Successfuladd(45),
    Successfulsell(46),
    Successfulswap(47),
    Notleader(50),
    Invalidmember(51),
    Playerdoesnotexist(52),
    Alreadyinguild(53),
    Guildnametooshort(54),
    Guildnametoolong(55),
    Insufficientfunds(56),
    Databaseerrorprobablynamealreadytaken(57),
    Invalidinvitationtarget(58),
    Notinaguild(59),
    Targetalreadyinguild(60),
    Notguildadministrator(61),
    Memberalreadyinvited(62),
    Noinvites(63),
    Invitedoesnotexist(64),
    Guilddoesnotexist(65),
    Cantkickanotheradmin(66),
    Successfulpromote(67),
    Successfuldemote(68),
    Successfulguildcreation(69),
    Successfulinvitesend(70),
    Successfulinviteresolution(71),
    Successfulleave(72),
    Successfulkick(73),
    Successfuldisband(74),
    Successfulstringupdate(95),
    Successfulgenderupdate(97),
    Successfulpushbulletupdate(99),
    Onlyoneturninthetimelimit(100),
    Onlyonecharacterregistrationinthetimelimit(101),
    Turntakensuccessfully(102),
    Mapretrievedsuccessfully(103),
    Invalidpriorityspecified(110),
    Invalidstat(111),
    Notenoughprioritypoints(112),
    Priorityshiftsuccess(113),
    Battlenotfound(120),
    Battlefoundsuccessfully(121),
    BattleIDnotvalidlength(122),

    // always have a backup plan ;)
    UNKNOWN(Integer.MAX_VALUE);

    /**
     * the response code (@see https://github.com/IdleLands/IdleLands/wiki/REST-Error-Codes)
     */
    int code;

    /**,.
     * Internal Constructor
     * @param code
     */
    StatusCode(int code)
    {
        this.code = code;
    }

    /**
     * Get Event enum by code
     * @param code
     * @return
     */
    public static StatusCode byCode(int code)
    {
        for(StatusCode type : StatusCode.values())
        {
            if(type.code == code)
                return type;
        }
        return UNKNOWN;
    }

}
