/*
 * Copyright (c) 2019 Abex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.cache.codeupdater.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.runelite.cache.codeupdater.git.GitUtil;
import org.eclipse.jgit.lib.Repository;
public class UpdateHandler
{
	public static int extractRevision(Repository repo, String commit) throws IOException
	{
		String oldCommitMessage = GitUtil.resolve(repo, commit).getShortMessage();
		Matcher commitMatcher = Pattern.compile("rev([0-9]+)").matcher(oldCommitMessage);
		commitMatcher.find();
		return Integer.parseInt(commitMatcher.group(1));
	}

	public static String calculateTag(Repository repo, int rev) throws IOException
	{
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String revSlug = "-rev" + rev;
		String tag = dateStr + revSlug;
		for (int c = 1; repo.resolve(tag) != null; c++)
		{
			tag = dateStr + "-c" + c + revSlug;
		}
		return tag;
	}
}
