package ladeEngine.Monitor;

import ladeEngine.Utils.M3IVector;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

public class PlaneMonitor {
    public Location location;
    public BlockFace face;
    public ArrayList<M3IVector> relativeLocations = new ArrayList<>();
}
