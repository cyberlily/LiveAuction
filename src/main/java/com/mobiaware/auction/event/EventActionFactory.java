package com.mobiaware.auction.event;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.mobiaware.auction.event.action.AuctionActionDelete;
import com.mobiaware.auction.event.action.AuctionActionEdit;
import com.mobiaware.auction.event.action.AuctionActionList;
import com.mobiaware.auction.event.action.AuctionActionListCount;
import com.mobiaware.auction.event.action.CategoryActionDelete;
import com.mobiaware.auction.event.action.CategoryActionEdit;
import com.mobiaware.auction.event.action.CategoryActionList;
import com.mobiaware.auction.event.action.CategoryActionListCount;
import com.mobiaware.auction.event.action.ItemActionDelete;
import com.mobiaware.auction.event.action.ItemActionEdit;
import com.mobiaware.auction.event.action.ItemActionList;
import com.mobiaware.auction.event.action.ItemActionListCount;
import com.mobiaware.auction.event.action.UserActionDelete;
import com.mobiaware.auction.event.action.UserActionEdit;
import com.mobiaware.auction.event.action.UserActionList;
import com.mobiaware.auction.event.action.UserActionListCount;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.InvalidAction;

public class EventActionFactory {
  private EventActionFactory() {
    // static only
  }

  public static Action getAction(final HttpServletRequest request) {
    Preconditions.checkNotNull(request);

    String paction = request.getParameter(HttpConstants.PARAM_ACTION);

    if (StringUtils.isBlank(paction)) {
      return (new InvalidAction());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONLIST)) {
      return (new AuctionActionList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONLISTCNT)) {
      return (new AuctionActionListCount());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONEDIT)) {
      return (new AuctionActionEdit());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONDELETE)) {
      return (new AuctionActionDelete());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_CATEGORYLIST)) {
      return (new CategoryActionList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_CATEGORYLISTCNT)) {
      return (new CategoryActionListCount());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_CATEGORYEDIT)) {
      return (new CategoryActionEdit());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_CATEGORYDELETE)) {
      return (new CategoryActionDelete());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMLIST)) {
      return (new ItemActionList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMLISTCNT)) {
      return (new ItemActionListCount());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMEDIT)) {
      return (new ItemActionEdit());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMDELETE)) {
      return (new ItemActionDelete());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_USERLIST)) {
      return (new UserActionList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_USERLISTCNT)) {
      return (new UserActionListCount());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_USEREDIT)) {
      return (new UserActionEdit());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_USERDELETE)) {
      return (new UserActionDelete());
    }

    return (new InvalidAction());
  }
}
