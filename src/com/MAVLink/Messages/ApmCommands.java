package com.MAVLink.Messages;

import java.util.ArrayList;

public enum ApmCommands
{
  private final int arduPilotIntValue;
  private final CommandType commandType;
  private final String name;

  static
  {
    CMD_NAV_LOITER_UNLIM = new ApmCommands("CMD_NAV_LOITER_UNLIM", 1, "Loiter", 17, CommandType.NAVIGATION);
    CMD_NAV_LOITER_TURNS = new ApmCommands("CMD_NAV_LOITER_TURNS", 2, "LoiterN", 18, CommandType.NAVIGATION);
    CMD_NAV_LOITER_TIME = new ApmCommands("CMD_NAV_LOITER_TIME", 3, "LoiterT", 19, CommandType.NAVIGATION);
    CMD_NAV_RETURN_TO_LAUNCH = new ApmCommands("CMD_NAV_RETURN_TO_LAUNCH", 4, "RTL", 20, CommandType.COMMAND);
    CMD_NAV_LAND = new ApmCommands("CMD_NAV_LAND", 5, "Land", 21, CommandType.NAVIGATION);
    CMD_NAV_TAKEOFF = new ApmCommands("CMD_NAV_TAKEOFF", 6, "Takeoff", 22, CommandType.NAVIGATION);
    CMD_NAV_ROI = new ApmCommands("CMD_NAV_ROI", 7, "ROI", 80, CommandType.COMMAND_WITH_TARGET);
    CMD_NAV_PATHPLANNING = new ApmCommands("CMD_NAV_PATHPLANNING", 8, "Path", 81, CommandType.COMMAND);
    CMD_DO_JUMP = new ApmCommands("CMD_DO_JUMP", 9, "Do Jump", 177, CommandType.COMMAND);
    CMD_DO_SET_HOME = new ApmCommands("CMD_DO_SET_HOME", 10, "Set Home", 179, CommandType.COMMAND_WITH_TARGET);
    CMD_DO_CHANGE_SPEED = new ApmCommands("CMD_DO_CHANGE_SPEED", 11, "Set Speed", 178, CommandType.COMMAND);
    CMD_CONDITION_CHANGE_ALT = new ApmCommands("CMD_CONDITION_CHANGE_ALT", 12, "Set Alt", 113, CommandType.NAVIGATION);
    CMD_CONDITION_DISTANCE = new ApmCommands("CMD_CONDITION_DISTANCE", 13, "Set Distance", 114, CommandType.COMMAND);
    CMD_CONDITION_YAW = new ApmCommands("CMD_CONDITION_YAW", 14, "Yaw to", 115, CommandType.COMMAND);
    ApmCommands[] arrayOfApmCommands = new ApmCommands[15];
    arrayOfApmCommands[0] = CMD_NAV_WAYPOINT;
    arrayOfApmCommands[1] = CMD_NAV_LOITER_UNLIM;
    arrayOfApmCommands[2] = CMD_NAV_LOITER_TURNS;
    arrayOfApmCommands[3] = CMD_NAV_LOITER_TIME;
    arrayOfApmCommands[4] = CMD_NAV_RETURN_TO_LAUNCH;
    arrayOfApmCommands[5] = CMD_NAV_LAND;
    arrayOfApmCommands[6] = CMD_NAV_TAKEOFF;
    arrayOfApmCommands[7] = CMD_NAV_ROI;
    arrayOfApmCommands[8] = CMD_NAV_PATHPLANNING;
    arrayOfApmCommands[9] = CMD_DO_JUMP;
    arrayOfApmCommands[10] = CMD_DO_SET_HOME;
    arrayOfApmCommands[11] = CMD_DO_CHANGE_SPEED;
    arrayOfApmCommands[12] = CMD_CONDITION_CHANGE_ALT;
    arrayOfApmCommands[13] = CMD_CONDITION_DISTANCE;
    arrayOfApmCommands[14] = CMD_CONDITION_YAW;
  }

  private ApmCommands(String paramString, int paramInt, CommandType paramCommandType)
  {
    this.name = paramString;
    this.arduPilotIntValue = paramInt;
    this.commandType = paramCommandType;
  }

  public static ApmCommands getCmd(int paramInt)
  {
    for (ApmCommands localApmCommands : values())
      if (paramInt == localApmCommands.getType())
        return localApmCommands;
    return null;
  }

  public static ApmCommands getCmd(String paramString)
  {
    for (ApmCommands localApmCommands : values())
      if (paramString.equals(localApmCommands.getName()))
        return localApmCommands;
    return null;
  }

  public static ArrayList<String> getNameList()
  {
    ArrayList localArrayList = new ArrayList();
    ApmCommands[] arrayOfApmCommands = values();
    int i = arrayOfApmCommands.length;
    for (int j = 0; j < i; j++)
      localArrayList.add(arrayOfApmCommands[j].getName());
    return localArrayList;
  }

  public String getName()
  {
    return this.name;
  }

  public int getType()
  {
    return this.arduPilotIntValue;
  }

  public boolean isOnFligthPath()
  {
    switch (1.$SwitchMap$com$MAVLink$Messages$ApmCommands$CommandType[this.commandType.ordinal()])
    {
    default:
      return false;
    case 3:
    }
    return true;
  }

  public boolean showOnMap()
  {
    switch (1.$SwitchMap$com$MAVLink$Messages$ApmCommands$CommandType[this.commandType.ordinal()])
    {
    default:
      return true;
    case 1:
    }
    return false;
  }

  private static enum CommandType
  {
    static
    {
      COMMAND = new CommandType("COMMAND", 1);
      COMMAND_WITH_TARGET = new CommandType("COMMAND_WITH_TARGET", 2);
      CommandType[] arrayOfCommandType = new CommandType[3];
      arrayOfCommandType[0] = NAVIGATION;
      arrayOfCommandType[1] = COMMAND;
      arrayOfCommandType[2] = COMMAND_WITH_TARGET;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Messages.ApmCommands
 * JD-Core Version:    0.6.2
 */