package com.mobiaware.auction.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import com.mobiaware.auction.live.LiveActionFactory;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.InvalidAction;

public class EventActionFactoryTest {
  @Test
  public void testShouldSucceed() {
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_AUCTIONLIST);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_AUCTIONLISTCNT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_AUCTIONEDIT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_AUCTIONDELETE);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_CATEGORYLIST);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_CATEGORYLISTCNT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_CATEGORYEDIT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_CATEGORYDELETE);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION))
        .thenReturn(HttpConstants.ACTION_ITEMLIST);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_ITEMLISTCNT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION))
        .thenReturn(HttpConstants.ACTION_ITEMEDIT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_ITEMDELETE);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION))
        .thenReturn(HttpConstants.ACTION_USERLIST);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_USERLISTCNT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION))
        .thenReturn(HttpConstants.ACTION_USEREDIT);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_USERDELETE);
    Assert.assertFalse(LiveActionFactory.getAction(request) instanceof InvalidAction);
  }

  @Test
  public void testShouldFail() {
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn("abcdef");
    Assert.assertTrue(LiveActionFactory.getAction(request) instanceof InvalidAction);
  }
}
