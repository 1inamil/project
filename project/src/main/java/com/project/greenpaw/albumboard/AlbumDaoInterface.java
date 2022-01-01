package com.project.greenpaw.albumboard;

public interface AlbumDaoInterface {

	AlbumListTO boardList(AlbumListTO listTO);

	int writeOk(AlbumTO to);

	AlbumTO view(AlbumTO to);

	int deleteOk(AlbumTO to);

	int modifyOk(AlbumTO to);

	AlbumListTO communityAlbumList(AlbumListTO listTO);

	AlbumTO CommunityAlbumView(AlbumTO to);

}