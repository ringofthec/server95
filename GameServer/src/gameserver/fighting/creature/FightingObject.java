package gameserver.fighting.creature;

import gameserver.fighting.Vector2;

public class FightingObject {
	private Vector2 m_Position = new Vector2();
    public Vector2 getPosition()
    {
    	return new Vector2(m_Position.x, m_Position.y);
    }
    public void setPosition(Vector2 value)
    {
    	m_Position.x = value.x;
    	m_Position.y = value.y;
    }
    public float getPositionX()
    {
    	return m_Position.x;
    }
    public void setPositionX(float x)
    {
    	m_Position.x = x;
    }
    public float getPositionY()
    {
        return m_Position.y;
    }
    public void setPositionY(float y)
    {
    	m_Position.y = y;
    }
    public void moveLeft(float deltaTime, float speed)
    {
        m_Position.x -= deltaTime * speed;
    }
    public void moveRight(float deltaTime, float speed)
    {
    	m_Position.x += deltaTime * speed;
    }
    public void moveUp(float deltaTime, float speed)
    {
    	m_Position.y += deltaTime * speed;
    }
    public void moveDown(float deltaTime, float speed)
    {
    	m_Position.y -= deltaTime * speed;
    }
    public float distanceX(Vector2 position)
    {
        return Math.abs(m_Position.x - position.x);
    }
    public float distanceX(float position)
    {
        return Math.abs(m_Position.x - position);
    }
    public float distanceY(Vector2 position)
    {
        return Math.abs(m_Position.y - position.y);
    }
    public float distanceY(float position)
    {
        return Math.abs(m_Position.y - position);
    }
    public float distance(Vector2 position)
    {
        return Vector2.Distance(m_Position, position);
    }
    public void OnUpdate(float deltaTime) throws Exception { }
}
