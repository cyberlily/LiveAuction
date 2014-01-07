package com.mobiaware.auction.live.action;

import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.eventbus.EventBus;
import com.mobiaware.auction.Device;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.auction.live.notify.NotificationEngine;
import com.mobiaware.auction.live.notify.PushNotification;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;

public class DeviceActionSendMessage extends Action {
  private transient final DataService _dataService = new MySqlDataServiceImpl();

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    String msg = getStringParameter(request, HttpConstants.PARAM_DESCRIPTION, null);

    checkRequiredString(msg, 128);

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    List<Device> devices = getDataService().getDevices(0, 500, null, null);
    List<Device> filteredDevices = new ArrayList<Device>(new HashSet<Device>(devices));

    ServletContext context = request.getServletContext();
    EventBus eventBus = (EventBus) context.getAttribute(NotificationEngine.EVENTBUS_REGISTRY);
    eventBus.post(new PushNotification(filteredDevices, msg));

    outputJsonReponse(request, response, this);
  }
}
