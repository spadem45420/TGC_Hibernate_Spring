--drop table PrivateGroupGamesItem;
--drop table PrivateChoiceGames;
--drop table PrivateGroupRoomMessage;
--drop table JoinerInfoPrivateGroupRoom;
--drop table PrivateGroupRoomInfo
--drop table PrivateGroupRoom;
--drop table StoreGroupChoiceGames;
--drop table StoreGroupRoomMessage;
--drop table JoinerInfoStoreGroup;
--drop table StoreGroupRoomInfo;
--drop table StoreGroupRoom;
drop table GroupChoiceGames;
drop table GroupRoom_Message;
drop table Joiner_Info;
drop table GroupRoom_Info;
drop table GroupRoom;
drop table BoardGames_Image;
drop table BoardGames;
drop table BoardGameKind;
drop table StoreScore;
drop table RentalTime;
drop table StoreInformation_Image;
drop table StoreInformation;
drop table TabuUsernameTable;
drop table Member_FavoredType;
drop table StoreMember;
drop table Administrator;
drop table Member;



--create 會員Member--
create table Member (
 memberId								  int IDENTITY (1,1),
 username								  varchar(30) not null, 
 pswd									  varbinary(50) not null,
 email									  varchar(50) not null,
 lastname								  varchar(20) not null,
 firstname								  varchar(20) not null,
 gender									  varchar(10),
 nickname								  nvarchar(20),
 birthday								  datetime,
 idCard									  varchar(20) not null,
 joinDate								  datetime,
 phone									  varchar(50),
 memberAddress							  varchar(100),
 imgFileName							  varchar(50),
 memberImage							  image,
 isGroupBan								  bit,
 isCommentBan							  bit,
 notBanTime								  datetime,
 banTime								  datetime,
constraint Member_primary_key primary key (memberId));

--create管理員	Administrator--
create table Administrator(
administratorId							  int IDENTITY (1,1),
adminUsername    						  varchar(30),
adminPswd								  varbinary(50),
imgFileName								  varchar(50),
adminMemberImage						  image,
constraint Administrator_primary_key primary key (administratorId));

--create店家會員	StoreMember--
create table StoreMember (
storeMemberId							  int IDENTITY (1,1),
storeUsername							  varchar(50),
storePswd								  varbinary(50),
storeJoinDate							  datetime,
storePhone								  varchar(50),
imgFileName								  varchar(50),
storeImage								  image,
storeEmail								  varchar(50),
storeWebsite							  varchar(100),
constraint StoreMember_primary_key primary key (storeMemberId));

--create會員喜好桌遊類型MemberFavoredType--
create table Member_FavoredType(
memberFavoredId							  int IDENTITY (1,1),
memberId								  int not null,
favoredType								  varchar(30),
constraint Member_FavoredType_memberId_fk foreign key (memberId) references Member(memberId),
constraint Member_FavoredType_primary_key primary key (memberFavoredId));

--create 檢舉名單審核表	BlackUsernameTable--
create table TabuUsernameTable(
tabuId									  int IDENTITY (1,1),
tabuMemberId							  int,
toTabuMemberId							  int,
tabuReason								  varchar(400),
constraint TabuUsernameTable_tabuMemberId_fk foreign key (tabuMemberId) references Member(memberId),
constraint TabuUsernameTable_toTabuMemberId_fk foreign key (toTabuMemberId) references Member(memberId),
constraint TabuUsernameTable_primary_key primary key (tabuId));

--create 專賣店資訊	StoreInformation--
create table StoreInformation(
storeMemberId							  int not null,
storeId									  int IDENTITY (1,1),
storeName								  varchar(30),
storeAddress							  varchar(100),
imgFileName								  varchar(50),
storeImage								  image,
storeTel								  varchar(50),
rentAreaCost							  float,
groupUpperLimit							  int,
constraint StoreInformation_storeMemberId_fk foreign key (storeMemberId) references StoreMember(storeMemberId),
constraint StoreInformation_primary_key primary key (storeId));

--create 專賣店圖片	StoreInformationImage--
create table StoreInformation_Image(
storeId      							  int not null,
storeImageId							  int IDENTITY (1,1),
boardGameHelp							  varchar(MAX),
imgFileName								  varchar(50),
areaImage								  image,
constraint StoreInformation_Image_storeId_fk foreign key (storeId) references StoreInformation(storeId),
constraint StoreInformation_Image_primary_key primary key (storeImageId));

--create 專賣店場地時段	RentalTime--
create table RentalTime(
storeId           						  int not null,
monStart								  datetime,
monEnd									  datetime,
tueStart								  datetime,
tueEnd									  datetime,
wedStart								  datetime,
wedEnd									  datetime,
thuStart								  datetime,
thuEnd									  datetime,
friStart								  datetime,
friEnd									  datetime,
satStart								  datetime,
satEnd									  datetime,
sunStart								  datetime,
sunEnd									  datetime,
constraint RentalTime_storeId_fk foreign key (storeId) references StoreInformation(storeId),
constraint RentalTime_primary_key primary key (storeId));

--專賣店評分	StoreScore--
create table StoreScore(
storeScoreId							  int IDENTITY (1,1),
storeId									  int not null,
memberId								  int not null,
storeScore								  float,
storeScoreReason						  varchar(400),
constraint StoreScore_memberId_fk foreign key (memberId) references Member(memberId),
constraint StoreScore_storeId_fk foreign key (storeId) references StoreInformation(storeId),
constraint StoreScore_primary_key primary key (storeScoreId));

--桌遊類型	BoardGameKind
create table BoardGameKind(
boardGameSerialNumber					  int,
boardGameStyle							  varchar(30),
constraint BoardGameKind_primary_key primary key (boardGameSerialNumber));

--(專賣店)桌遊資訊	BoardGames--
create table BoardGames(
boardGamesId							  int IDENTITY (1,1),
storeMemberId						      int not null,
storeName							      varchar(30),
boardGameEnglishName					  varchar(30),
boardGameName							  varchar(50),
boardGameSerialNumber					  int,
boardGameNumber							  varchar(10),
imgFileName								  varchar(50),
boardGameImage							  image,
boardGameExplan							  varchar(MAX),
constraint BoardGames_primary_key primary key (boardGamesId),
constraint BoardGames_storeMemberId_fk foreign key (storeMemberId) references StoreMember (storeMemberId),
constraint BoardGames_boardGameId_fk foreign key (boardGameSerialNumber) references BoardGameKind (boardGameSerialNumber));

--桌遊圖片	BoardGamesImage--
create table BoardGames_Image(
boardGamesId							  int,
storeImageId							  int IDENTITY (1,1),
imgFileName								  varchar(50),
boardGameImages					          image,
constraint BoardGames_Image_boardGamesId_fk foreign key (boardGamesId) references BoardGames (boardGamesId),
constraint BoardGames_Image_primary_key primary key (storeImageId));

--私人房間(店家場)	GroupRoom--
create table GroupRoom(
groupSerialNumber						  int IDENTITY (1,1),
storeMemberId							  int not null,
storeName								  varchar(30),
memberId								  int not null,
groupStartTime							  datetime,
groupEndTime							  datetime,
groupRoomName							  varchar(20),
groupSuggestNumber						  varchar(20),
groupLowerLimit							  int,
groupUpperLimit							  int,
groupGameTime				              datetime,
reserveGroupStartTime					  datetime,
reserveGroupEndTime			  			  datetime,
roomState								  int,
imgFileName	                              varchar(50),
privateGroupImage						  image,
constraint GroupRoom_primary_key primary key (groupSerialNumber),
constraint GroupRoom_memberId_fk foreign key (memberId) references Member (memberId),
constraint GroupRoom_storeMemberId_fk foreign key (storeMemberId) references StoreMember (storeMemberId));

--私人房間資訊(店家場)	GroupRoomInfo--
create table GroupRoom_Info(
groupSerialNumber						  int,
groupPicture							  image,
imgFileName	                              varchar(50),
groupPictureSerialNumber				  int IDENTITY (1,1),
constraint GroupRoom_Info_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint GroupRoom_Info_primary_key primary key (groupPictureSerialNumber));


--入團者(私人房間店家場)資訊 	Joiner_Info
create table Joiner_Info(
joiner_InfoSerialNumber					  int IDENTITY (1,1),
groupSerialNumber						  int,
joinTime								  datetime,
username			    				  varchar(30),
constraint Joiner_Info_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint Joiner_Info_primary_key primary key (joiner_InfoSerialNumber));

--私人房間(店家場)留言	GroupRoomMessage
create table GroupRoom_Message(
groupRoomMessageSerialNumber			  int IDENTITY (1,1),
groupSerialNumber						  int,
messageUsername							  varchar(30),
messageContents							  varchar(400),
messageTime							      dateTime,
constraint GroupRoom_Message_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint GroupRoom_Message_primary_key primary key (groupRoomMessageSerialNumber));

--私人房間(店家場)所選桌遊	GroupChoiceGames
create table GroupChoiceGames(
choiceGamesSerialNumber					  int IDENTITY (1,1),
groupSerialNumber						  int,
boardGameSerialNumber					  int,
boardGameName							  varchar(50),
constraint GroupChoiceGames_primary_key primary key (choiceGamesSerialNumber),
constraint GroupChoiceGames_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint GroupChoiceGames_boardGameSerialNumber_fk foreign key (boardGameSerialNumber) references BoardGameKind (boardGameSerialNumber));

----店家活動	StoreGroupRoom
--create table StoreGroupRoom(
--groupSerialNumber						  int IDENTITY (1,1),
--storeUsername							  varchar(50),
--storeId									  int,
--groupUsername							  varchar(30),
--groupTime								  datetime,
--groupRoomName							  varchar(20),
--groupSuggestNumber						  int,
--groupLowerLimit							  int,
--groupUpperLimit							  int,
--groupGameTime							  int,
--acticityStartTime						  datetime,
--acticityEndTime							  datetime,
--constraint StoreGroupRoom_primary_key primary key (groupSerialNumber),
--constraint StoreGroupRoom_groupUsername_fk foreign key (groupUsername) references Member (username));

----店家活動資訊	StoreGroupRoomInfo
--create table StoreGroupRoomInfo(
--groupSerialNumber						  int,
--imgFileName	                              varchar(50),
--activityPicture							  image,
--pictureSerialNumber						  int IDENTITY (1,1),
--constraint StoreGroupRoomInfo_primary_key primary key (PictureSerialNumber),
--constraint StoreGroupRoomInfo_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber));

----入團者(店家活動資訊)	JoinerInfoStoreGroup
--create table JoinerInfoStoreGroup(
--groupSerialNumber						  int,
--joinTime								  datetime,
--username								  varchar(30),
--constraint JoinerInfoStoreGroup_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber),
--constraint JoinerInfoStoreGroup_primary_key primary key (groupSerialNumber));

----店家活動留言	StoreGroupRoomMessage
--create table StoreGroupRoomMessage(
--storeGroupRoomMessageSerialNumber		  int IDENTITY (1,1),
--groupSerialNumber						  int,
--storeUsername							  varchar(30),
--messageUsername							  varchar(30),
--messageContents							  varchar(400),
--messageTime								  dateTime,
--constraint StoreGroupRoomMessage_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber),
--constraint StoreGroupRoomMessage_primary_key primary key (storeGroupRoomMessageSerialNumber));

----店家房間(店家場)所選桌遊	StoreGroupChoiceGames
--create table StoreGroupChoiceGames(
--choiceGamesSerialNumber					  int IDENTITY (1,1),
--groupSerialNumber						  int,
--boardGameStyle							  varchar(30),
--boardGameName							  varchar(50),
--constraint StoreGroupChoiceGames_primary_key primary key (choiceGamesSerialNumber),
--constraint StoreGroupChoiceGames_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber),
--constraint StoreGroupChoiceGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind (boardGameStyle));


----create私人房間私人場--
--create table PrivateGroupRoom(
--privateGroupSerialNumber				  int IDENTITY (1,1),
--privateGroupUsername					  varchar(30),
--privateGroupRoomName					  varchar(20),
--boardGameStyle							  varchar(30),
--privateGroupSuggestNumber				  int,
--privateGroupUpperLimit	                  int,
--privateGroupLocation					  varchar(20),
--privateGroupAddress						  varchar(100),
--privateGroupCost						  float,
--privateGroupExplain						  varchar(MAX),
--privateGroupStartTime					  datetime,
--imgFileName	                              varchar(50),
--privateGroupImage						  image,
--privateGroupInformation					  varchar(MAX)
--constraint PrivateGroupRoom_primary_key primary key (privateGroupSerialNumber),
--constraint PrivateGroupRoom_privateGroupUsername_fk foreign key (privateGroupUsername) references Member(username),
--constraint PrivateGroupRoom_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind(boardGameStyle));

----私人房間資訊(私人場)	PrivateGroupRoomInfo
--create table PrivateGroupRoomInfo(
--privateGroupSerialNumber				  int,
--imgFileName	                              varchar(50),
--privateGroupPicture						  image,
--privateGroupPictureSerialNumber			  int IDENTITY (1,1),
--constraint PrivateGroupRoomInfo_primary_key primary key (privateGroupPictureSerialNumber),
--constraint PrivateGroupRoomInfo_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber));

----入團者(私人房間私人場)資訊	JoinerInfoPrivateGroupRoom
--create table JoinerInfoPrivateGroupRoom(
--privateGroupSerialNumber				  int,
--joinTime								  datetime,
--username								  varchar(30),
--constraint JoinerInfoPrivateGroupRoom_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber),
--constraint JoinerInfoPrivateGroupRoom_primary_key primary key (privateGroupSerialNumber));

----私人房間(私人場)留言	PrivateGroupRoomMessage
--create table PrivateGroupRoomMessage(
--privateGroupRoomMessageSerialNumber		  int IDENTITY (1,1),
--privateGroupSerialNumber				  int,
--messageUsername							  varchar(30),
--messageContents							  varchar(400),
--messageTime								  dateTime,
--constraint PrivateGroupRoomMessage_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber),
--constraint PrivateGroupRoomMessage_primary_key primary key (privateGroupRoomMessageSerialNumber));

----私人房間(私人場)所選桌遊	PrivateChoiceGames
--create table PrivateChoiceGames(
--choiceGamesSerialNumber					  int IDENTITY (1,1),
--privateGroupSerialNumber				  int,
--boardGameStyle							  varchar(30),
--boardGameName							  varchar(50),
--constraint PrivateChoiceGames_primary_key primary key (choiceGamesSerialNumber),
--constraint PrivateChoiceGames_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber),
--constraint PrivateChoiceGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind(boardGameStyle));

----私人團遊玩項目 	PrivateGroupGamesItem
--create table PrivateGroupGamesItem(
--privateGroupSerialNumber				  int,
--privateGroupGames						  varchar(20),
--constraint PrivateGroupGamesItem_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber),
--constraint PrivateGroupGamesItem_primary_key primary key (privateGroupSerialNumber));