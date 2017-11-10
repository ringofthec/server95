package gameserver.utils;

import com.commons.util.CachePool;
import com.commons.util.DatabaseInsertUpdateResult;

import databaseshare.DatabaseVideo;

public class VideoManager {
	private static VideoManager instance = new VideoManager();
	public static VideoManager GetInstance() {
		return instance;
	}
	
	CachePool<Integer, DatabaseVideo> m_videoArray = new CachePool<Integer, DatabaseVideo>(10 * 60);
	 
	public DatabaseVideo GetVideo(int video_id)
	{
		if (m_videoArray.isCache(video_id))
			return m_videoArray.getData(video_id);
		
		DatabaseVideo video = DbMgr.getInstance().getShareDB().SelectOne(DatabaseVideo.class, "video_id = ?", video_id);
		m_videoArray.cache(video_id, video);
		return video;
	}
	public DatabaseVideo InsertVideo(DatabaseVideo video)
	{
		if (video.video_data.length <= 0) {
			video.video_id = 0;
			return null;
		}
		
		DatabaseInsertUpdateResult result = DbMgr.getInstance().getShareDB().Insert(video);
		if (result.succeed)
		{
			video.video_id = result.identity.intValue();
			m_videoArray.cache(video.video_id, video);
			return video;
		}
		return null;
	}
}
