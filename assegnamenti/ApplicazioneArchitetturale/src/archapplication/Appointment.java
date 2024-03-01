package archapplication;

/**
*
* The class {@code Appointment} provides a simple appointment model.
*
**/
public class Appointment {
	private String DocName;
	private String DateTime;
	private String Place;
	private String Type;

	private int AppId;
	private int PatientId;

	/**
	 * Class constructor.
	 *
	 * @param dn doctor name.
	 * @param dt date and time.
	 * @param p place.
	 * @param ai appointment id.
	 * @param pi patient id.
	 * @param t type.
	 */
	public Appointment(final String dn, final String dt, final String p, final String t, final int ai, final int pi)
	{
		this.DocName=dn;
		this.DateTime=dt;
		this.Place=p;
		this.Type=t;
		this.AppId=ai;
		this.PatientId=pi;

	}
	
	/**
	 * Gets Doctor name.
	 *
	 * @return Doctor Name.
	 *
	 **/
	public String getDocName() {
		return DocName;
	}

	/**
	 * Sets Doctor name.
	 *
	 * @param Doctor name.
	 *
	 **/
	public void setDocName(String docName) {
		DocName = docName;
	}

	/**
	 * Gets the date of the appointment.
	 *
	 * @return Date.
	 *
	 **/
	public String getDateTime() {
		return DateTime;
	}

	/**
	 * Sets Date.
	 *
	 * @param Date.
	 *
	 **/
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	/**
	 * Gets the place of the appointment.
	 *
	 * @return Place.
	 *
	 **/
	public String getPlace() {
		return Place;
	}

	/**
	 * Sets Place.
	 *
	 * @param Place.
	 *
	 **/
	public void setPlace(String place) {
		Place = place;
	}

	/**
	 * Gets the type of the visit.
	 *
	 * @return Type.
	 *
	 **/
	public String getType() {
		return Type;
	}
	
	/**
	 * Sets Type
	 *
	 * @param Type.
	 *
	 **/
	public void setType(String type) {
		Type = type;
	}

	/**
	 * Gets Appointment's id.
	 *
	 * @return id.
	 *
	 **/
	public int getAppId() {
		return AppId;
	}

	/**
	 * Sets AppId.
	 *
	 * @param AppId.
	 *
	 **/
	public void setAppId(int appId) {
		AppId = appId;
	}

	/**
	 * Gets Patient id.
	 *
	 * @return id.
	 *
	 **/
	public int getPatientId() {
		return PatientId;
	}

	/**
	 * Sets PatientId.
	 *
	 * @param PatientId.
	 *
	 **/
	public void setPatientId(int patientId) {
		PatientId = patientId;
	}

}
