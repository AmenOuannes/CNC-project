package equipe06.Domaine.Utils;

import equipe06.Domaine.Repere;

import java.awt.*;

public class ZoneInterditeDTO {
    private Point OrigineDTO;
    private Point DestinoDTO;

    public ZoneInterditeDTO(ZoneInterdite zoneInterdite) {
        if (zoneInterdite != null) {
            OrigineDTO = zoneInterdite.getOrigin();
            DestinoDTO = zoneInterdite.getDestination();
        }
    }

    public Point getDestinoDTO() {
        return DestinoDTO;
    }

    public Point getOrigineDTO() {
        return OrigineDTO;
    }
}
