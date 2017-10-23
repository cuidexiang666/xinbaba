package cn.itcast.babasport.pojo.ad;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Advertising implements Serializable {
    /**
     * ���ID
     */
    private Long id;

    /**
     * ���λ��ID
     */
    private Long positionId;

    /**
     * ������
     */
    private String title;

    /**
     * ���ͼƬʱ����������
     */
    @JsonProperty("href")
    private String url;

    /**
     * ͼƬ���·��
     */
    @JsonProperty("src")
    private String picture;

    /**
     * ���ͼƬ�ĸ�
     */
    private Integer height;

    /**
     * ���ͼƬ�Ŀ�
     */
    private Integer width;

    private static final long serialVersionUID = 1L;
    
    private Position position;
    
    public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", positionId=").append(positionId);
        sb.append(", title=").append(title);
        sb.append(", url=").append(url);
        sb.append(", picture=").append(picture);
        sb.append(", height=").append(height);
        sb.append(", width=").append(width);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}