package equipe06.Domaine.Utils;

public class ZoneInterditeDTO {
    private float longueurDTO;
    private float largeurDTO;
    private float DistanceToXDTO;
    private float DistanceToYDTO;

<<<<<<< Updated upstream
   public ZoneInterditeDTO(ZoneInterdite zoneInterdite) {
    if (zoneInterdite != null) {
        this.longueurDTO = zoneInterdite.getLongueur();
=======
    public ZoneInterditeDTO(ZoneInterdite zoneInterdite) {
//        this.longueurDTO = zoneInterdite.getLongueur();
>>>>>>> Stashed changes
        this.largeurDTO = zoneInterdite.getLargeur();
        this.DistanceToXDTO = zoneInterdite.getDistanceToX();
        this.DistanceToYDTO = zoneInterdite.getDistanceToY();
    } 
}

    public float getLongueurDTO() {
        return longueurDTO;
    }
    public void setLongueurDTO(float longueurDTO) {
        this.longueurDTO = longueurDTO;
    }

    public float getDistanceToXDTO() {
        return DistanceToXDTO;
    }

    public float getDistanceToYDTO() {
        return DistanceToYDTO;
    }
}
