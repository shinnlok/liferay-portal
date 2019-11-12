create table AnalyticsMessageEntry (
	mvccVersion LONG default 0 not null,
	analyticsMessageEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	destination VARCHAR(75) null,
	message VARCHAR(75) null,
	retryCount INTEGER,
	sentDate DATE null,
	status INTEGER
);