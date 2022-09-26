import classes from "../CaseDetails.module.scss";

const CaseDetails = ({ numberOfHDD, numberOfSSD }) => (
  <table className={classes.customCardDetails}>
    <tbody>
      <tr>
        <td>Sloturi HDD: {numberOfHDD}</td>
      </tr>
      <tr>
        <td>Sloturi SSD: {numberOfSSD}</td>
      </tr>
    </tbody>
  </table>
);

export default CaseDetails;
