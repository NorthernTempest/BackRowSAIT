<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="col-12">
	<div class=row>
	<div class=col-md-6>
	<h1>
		User Settings
	</h1>

	<h4>
		Create New Account
	</h4>
	<form action=admin?action=createNewTaxPreparer method=POST>
		<input type=submit class="btn btn-primary" value="Create New Tax Preparer Account">
	</form><br>
	<form action=admin?action=createNewAdmin method=POST>
		<input type=submit class="btn btn-primary" value="Create New Admin Account">
	</form><br>

    <h4>
        Restore Account
    </h4>
    <form action=admin?action=restore method=POST>
        <p>Enter the email of the account you wish to restore:
            <input type=email name=restoreEmail id=restoreEmail maxlength=100 class=form-control required>
            <input type=submit class="btn btn-primary" value="Restore Account"></p>
    </form><br>

    <h4>
        Edit Account
    </h4>
    <form action=admin method=POST>
        <p>Enter the email of the account you wish to edit:
            <input type="hidden" name="action" value="editUser">
            <input type=email name="editUser" id="editUser" maxlength=100 class=form-control required>
            <input type=submit class="btn btn-primary" value="Edit Account"></p>
    </form><br>

	<h4>
		Deactivate Account
	</h4>
	<form action=admin?action=deactivate method=POST>
	<p>Enter the email of the account you wish to deactivate: 
	<input type=email name=deactivateEmail id=deactivateEmail maxlength=100 class=form-control required>
	<input type=submit class="btn btn-danger" value="Deactivate Account"></p>
	</form>
	</div>
	</div>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h1>
		Backup &amp; Restore
	</h1>
	<b>Last Time Backed Up:</b>
    <span id="lastBackedUp">${lastBackedUp}</span><br>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#backupModal">
        Backup Users and Messages
    </button>
	<br>
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#restoreModal">
		Restore Users and Messages
	</button>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h1>
		Configuration Settings
	</h1>
	<br><br><br><br>
</div>

<jsp:directive.include file="../template/foot.jsp" />
<%-- Modals --%>
<div class="modal fade" id="backupModal" tabindex="-1" role="dialog" aria-labelledby="backupModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="backupModalLabel">Backup</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				This will save a file containing current user accounts and current messages.
				User files will not be backed up.
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<form action="/backup" method="POST">
					<input type="hidden" name="action" value="backup">
					<button type="submit" class="btn btn-primary">Download Backup</button>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="restoreModal" tabindex="-1" role="dialog" aria-labelledby="restoreModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="restoreModalLabel">Restore</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					This will save a file containing current user accounts and current messages.
					User files will not be backed up.
					<div class="form-group">
						<form id="restoreForm" action="/backup" method="POST" enctype="multipart/form-data">
							<label for="restoreFile">Restore File</label>
							<input type="file" class="form-control" id="restoreFile" name="multiPartServlet" required>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					<button type="submit" form="restoreForm" class="btn btn-primary">Restore</button>

				</div>
			</div>
	</div>
</div>