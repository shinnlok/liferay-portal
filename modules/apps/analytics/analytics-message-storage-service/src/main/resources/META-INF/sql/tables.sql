create table AnalyticsMessageEntry (
	mvccVersion LONG default 0 not null,
	analyticsMessageEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	message BLOB
);