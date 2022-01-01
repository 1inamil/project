package com.project.greenpaw.albumboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumDaoImpl implements AlbumDaoInterface {

	@Autowired
	private AlbumDAO albumDao;
	
	@Override
	public AlbumListTO boardList(AlbumListTO listTO) {
		// TODO Auto-generated method stub
		return albumDao.boardList(listTO);
	}

	@Override
	public int writeOk(AlbumTO to) {
		// TODO Auto-generated method stub
		return albumDao.writeOk(to);
	}

	@Override
	public AlbumTO view(AlbumTO to) {
		// TODO Auto-generated method stub
		return albumDao.view(to);
	}

	@Override
	public int deleteOk(AlbumTO to) {
		// TODO Auto-generated method stub
		return albumDao.deleteOk(to);
	}

	@Override
	public int modifyOk(AlbumTO to) {
		// TODO Auto-generated method stub
		return albumDao.modifyOk(to);
	}

	@Override
	public AlbumListTO communityAlbumList(AlbumListTO listTO) {
		// TODO Auto-generated method stub
		return albumDao.communityAlbumList(listTO);
	}

	@Override
	public AlbumTO CommunityAlbumView(AlbumTO to) {
		// TODO Auto-generated method stub
		return albumDao.CommunityAlbumView(to);
	}

}
