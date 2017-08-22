package org.kohsuke.github;

import static org.kohsuke.github.Previews.SQUIRREL_GIRL;

import java.io.IOException;
import java.net.URL;

public class GHPullRequestReview {

	private int id;
	private GHUser user;
	private String body;
	private String commit_id;
	private String state;
	private String html_url;
	private String pull_request_url;
	private GHPullRequest owner;
	private String submitted_at;


	public int getId() {
		return id;
	}


	public GHUser getUser() {
		return user;
	}


	public String getBody() {
		return body;
	}


	public String getCommit_id() {
		return commit_id;
	}


	public String getState() {
		return state;
	}


	public String getHtml_url() {
		return html_url;
	}


	public String getPull_request_url() {
		return pull_request_url;
	}


	public void setPull_request_url(String pull_request_url) {
		this.pull_request_url = pull_request_url;
	}


	public void wrapUp(GHPullRequest ghPullRequest) {
        this.owner = ghPullRequest;
		
	}


	
    @Preview @Deprecated
    public PagedIterable<GHReaction> listReactions() {
        return new PagedIterable<GHReaction>() {
            public PagedIterator<GHReaction> _iterator(int pageSize) {
                return new PagedIterator<GHReaction>(owner.root.retrieve().withPreview(SQUIRREL_GIRL).asIterator(getApiRoute()+"/reactions", GHReaction[].class, pageSize)) {
                    @Override
                    protected void wrapUp(GHReaction[] page) {
                        for (GHReaction c : page)
                            c.wrap(owner.root);
                    }
                };
            }
        };
    }


    @Preview @Deprecated
    public GHReaction createReaction(ReactionContent content) throws IOException {
        return new Requester(owner.root)
                .withPreview(SQUIRREL_GIRL)
                .with("content", content.getContent())
                .to(getApiRoute()+"/reactions", GHReaction.class).wrap(owner.root);
    }

	
    private String getApiRoute() {
        return "/repos/"+owner.getRepository().getOwnerName()+"/"+owner.getRepository().getName()+"/issues/comments/" + id;
    }
    
	public String getSubmitted_at() {
		return submitted_at;
	}


}
