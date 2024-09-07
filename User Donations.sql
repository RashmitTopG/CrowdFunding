DELIMITER //
CREATE PROCEDURE GetUserDonationSummary(IN p_username VARCHAR(255))
BEGIN
    SELECT
        u.userid AS UserID,
        u.username AS Username,
        u.name AS Name,
        d.project_id AS ProjectID,
        p.name AS ProjectName,
        COUNT(d.donation_id) AS TotalDonations,
        SUM(d.amount) AS TotalAmountDonated,
        GROUP_CONCAT(d.donation_id) AS DonationIDs,
        GROUP_CONCAT(d.amount) AS IndividualDonations,
        GROUP_CONCAT(d.donation_time) AS DonationDates
    FROM donation d
    INNER JOIN login u ON d.user_id = u.userid
    INNER JOIN images p ON d.project_id = p.project_id
    WHERE u.username = p_username
    GROUP BY u.userid, u.username, u.name, d.project_id, p.name;
END //
DELIMITER ;





drop procedure GetUserDonationSummary ;
